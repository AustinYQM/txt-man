package com.yqmonline.world

import Entity
import com.yqmonline.attributes.Vision
import com.yqmonline.blocks.GameBlock
import com.yqmonline.extensions.AnyGameEntity
import com.yqmonline.extensions.blocksVision
import com.yqmonline.extensions.position
import org.hexworks.amethyst.api.Engine
import org.hexworks.amethyst.internal.TurnBasedEngine
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.builder.game.GameAreaBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.shape.EllipseFactory
import org.hexworks.zircon.api.shape.LineFactory
import org.hexworks.zircon.api.uievent.UIEvent
import kotlin.math.abs

class World(
    startingBlocks: Map<Position3D, GameBlock>,
    override val visibleSize: Size3D,
    override val actualSize: Size3D,
) : GameArea<Tile, GameBlock> by GameAreaBuilder
        .newBuilder<Tile, GameBlock>()
        .withVisibleSize(visibleSize)
        .withActualSize(actualSize)
        .build() {
    private val engine: TurnBasedEngine<GameContext> = Engine.create()

    init {
        startingBlocks.forEach { (pos, block) ->
            setBlockAt(pos, block)
            block.entities.forEach { entity ->
                engine.addEntity(entity)
                entity.position = pos
            }
        }
    }

    /**
     * Adds the given [Entity] at the given [Position3D]. Has no effect if this world already contains the given
     * [Entity].
     */
    fun addEntity(
        entity: AnyGameEntity,
        position: Position3D,
    ) {
        entity.position = position
        engine.addEntity(entity)
        fetchBlockAt(position).map { it.addEntity(entity) }
    }

    fun addAtEmptyPosition(
        entity: AnyGameEntity,
        offset: Position3D = Position3D.create(0, 0, 0),
        size: Size3D = actualSize,
    ): Boolean =
        findEmptyLocationWithin(offset, size)
            .fold(
                whenEmpty = { false },
                whenPresent = { location ->
                    addEntity(entity, location)
                    true
                },
            )

    /** Finds an empty location within the given area (offset and size) on this [World]. */
    fun findEmptyLocationWithin(
        offset: Position3D,
        size: Size3D,
    ): Maybe<Position3D> {
        var position = Maybe.empty<Position3D>()
        val maxTries = 10
        var currentTry = 0
        while (position.isPresent.not() && currentTry < maxTries) {
            val pos =
                Position3D.create(
                    x = (Math.random() * size.xLength).toInt() + offset.x,
                    y = (Math.random() * size.yLength).toInt() + offset.y,
                    z = (Math.random() * size.zLength).toInt() + offset.z,
                )
            fetchBlockAt(pos).map {
                if (it.isEmptyFloor) {
                    position = Maybe.of(pos)
                }
            }
            currentTry++
        }
        return position
    }

    fun moveEntity(
        entity: AnyGameEntity,
        position: Position3D,
    ): Boolean {
        var success = false
        val oldBlock = fetchBlockAt(entity.position)
        val newBlock = fetchBlockAt(position)

        if (bothBlocksPresent(oldBlock, newBlock)) {
            success = true
            oldBlock.get().removeEntity(entity)
            entity.position = position
            newBlock.get().addEntity(entity)
        }
        return success
    }

    private fun bothBlocksPresent(
        oldBlock: Maybe<GameBlock>,
        newBlock: Maybe<GameBlock>,
    ) = oldBlock.isPresent && newBlock.isPresent

    fun update(
        screen: Screen,
        uiEvent: UIEvent,
        game: Game,
    ) {
        engine.executeTurn(
            GameContext(
                world = this,
                screen = screen,
                uiEvent = uiEvent,
                player = game.player,
            ),
        )
    }

    fun removeEntity(entity: AnyGameEntity) {
        fetchBlockAt(entity.position).map { it.removeEntity(entity) }
        engine.removeEntity(entity)
        entity.position = Position3D.unknown()
    }

    fun isVisionBlockedAt(pos: Position3D): Boolean =
        fetchBlockAt(pos).fold(whenEmpty = { false }, whenPresent = {
            it.entities.any(AnyGameEntity::blocksVision)
        })

    fun findVisiblePositionsFor(entity: AnyGameEntity): Iterable<Position> {
        val centerPos = entity.position.to2DPosition()

        return entity
            .findAttribute(Vision::class)
            .map { (radius) ->
                EllipseFactory
                    .buildEllipse(
                        fromPosition = centerPos,
                        toPosition = centerPos.withRelativeY(radius).withRelativeX(radius),
                    ).positions
                    .flatMap { ringPos ->
                        val result = mutableListOf<Position>()
                        val iter = LineFactory.buildLine(centerPos, ringPos).iterator()
                        do {
                            val next = iter.next()
                            result.add(next)
                        } while (iter.hasNext() && isVisionBlockedAt(Position3D.from2DPosition(next, entity.position.z)).not())
                        result
                    }
            }.orElse(listOf())
    }

    fun addWorldEntity(entity: AnyGameEntity) {
        engine.addEntity(entity)
    }

    fun findPath(
        looker: AnyGameEntity,
        target: AnyGameEntity,
    ): List<Position> {
        var result = listOf<Position>()
        looker.findAttribute(Vision::class).map { (radius) ->
            val level = looker.position.z
            if (looker.position.isWithinRangeOf(target.position, radius)) {
                val path = LineFactory.buildLine(looker.position.to2DPosition(), target.position.to2DPosition())
                if (path.none { isVisionBlockedAt(it.toPosition3D(level)) }) {
                    result = path.positions.toList().drop(1)
                }
            }
        }
        return result
    }

    private fun Position3D.isWithinRangeOf(
        other: Position3D,
        radius: Int,
    ): Boolean =
        this.isUnknown.not() &&
            other.isUnknown.not() &&
            this.z == other.z &&
            abs(x - other.x) + abs(y - other.y) <= radius
}
