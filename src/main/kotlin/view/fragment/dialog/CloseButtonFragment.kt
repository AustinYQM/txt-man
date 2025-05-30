package com.yqmonline.view.fragment.dialog

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

class CloseButtonFragment(
    modal: Modal<EmptyModalResult>,
    parent: Container,
) : Fragment {
    /**
     * The [Component] this [Fragment] contains.
     */
    override val root: Component =
        Components
            .button()
            .withText("Close")
            .withAlignmentWithin(parent, ComponentAlignment.BOTTOM_RIGHT)
            .build()
            .apply {
                onActivated {
                    modal.close(EmptyModalResult)
                }
            }
}
