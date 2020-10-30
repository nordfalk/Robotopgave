package dk.nordfalk.robotopgave.ui.s3_execute;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import dk.nordfalk.robotopgave.model.Position;
import dk.nordfalk.robotopgave.model.Retning;
import dk.nordfalk.robotopgave.model.Rum;

public class RumView extends View {
    private Paint brikStregtype = new Paint();
    private Rum rum = new Rum(5, 5, new Position(2, 0, Retning.E));

    private float skærmSkala = 1;
    {
        brikStregtype.setColor(Color.GRAY);
        brikStregtype.setStyle(Paint.Style.FILL);
        brikStregtype.setAntiAlias(true);
        brikStregtype.setStrokeWidth(1);
    }

    // programmatisk konstruktør
    public RumView(Context context) {
        super(context);
    }

    public RumView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
    }

    public float getSkærmkoordinat(int logiskKoordinat) {
        return skærmSkala * 10 * (logiskKoordinat + 0.5f);
    }

    public void setRum(Rum nytRum) {
        rum = nytRum;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas c) {
        // Spillet er beregnet til en skærm der er 10 punkter bred per celle i rummet
        skærmSkala = Math.min(
                getWidth() / (rum.bredde * 10f ),
                getHeight() / (rum.højde * 10f )); // ... så skalér derefter
        c.scale(skærmSkala, skærmSkala);

        // Tegn felter
        for (int y = 0; y <= rum.højde; y++) {
            c.drawLine(0, y * 10, rum.bredde * 10, y * 10, brikStregtype);
        }
        for (int x = 0; x <= rum.bredde; x++) {
            c.drawLine(x * 10, 0, x * 10, rum.højde * 10, brikStregtype);
        }
        /*
        for (int i = 0; i <= rum.højde; i++) {
            c.drawLine(i * 10, 0, i * 10, 100, brikStregtype);
            c.drawLine(0, i * 10, 100, i * 10, brikStregtype);
        }*/
    }
}
