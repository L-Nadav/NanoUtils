package com.kyozm.nanoutils.gui.widgets.containers;

import com.kyozm.nanoutils.gui.widgets.Widget;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Stacked extends Submenu {

    public Stacked(Widget parent, List<Widget> widgets) {
        super(parent);
        this.plainElements = widgets;
        Optional<Widget> longestWidget = getLongestWidget();
        if (!longestWidget.isPresent()) return;
        setDimensions(longestWidget.get());
    }

    private void setDimensions(Widget longestWidget) {
        width = longestWidget.width + 6;
        height = 0;
        for (Widget w : plainElements) {
            w.parent = this;
            w.screenY = screenY + height;
            w.screenX = screenX;
            w.width = width;
            height += w.height + 1;
        }
    }

    private Optional<Widget> getLongestWidget() {
        return plainElements.stream().max(Comparator.comparing(Widget::width));
    }

    @Override
    public void render() {
        super.render();
        plainElements.forEach(Widget::render);
    }
}
