package task5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class Color extends RecursiveTask<Void> {
    int[] colors;
    int step;

    public Color(int[] colors, int step) {
        this.colors = colors;
        this.step = step;
    }

    @Override
    protected Void compute() {
        if (step == Main.N) {
            Main.printColors(colors);
            return null;
        }

        List<Color> colorList = new ArrayList<>();

        // for the node at position step try all possible colors
        for (int i = 0; i < Main.COLORS; i++) {
            int[] newColors = colors.clone();
            newColors[step] = i;
            if (Main.verifyColors(newColors, step)) {
                Color color = new Color(newColors, step + 1);
                colorList.add(color);
                color.fork();
            }
        }
        colorList.forEach(Color::join);
        return null;
    }
}
