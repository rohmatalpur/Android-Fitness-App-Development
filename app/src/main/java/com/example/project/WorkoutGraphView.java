package com.example.project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom view for displaying workout data as a smooth line graph
 * Inspired by JetLagged's sleep tracking visualization
 */
public class WorkoutGraphView extends View {

    private Paint linePaint;
    private Paint fillPaint;
    private Paint gridPaint;
    private Paint textPaint;
    private Paint barPaint;
    private Path linePath;
    private Path fillPath;

    private List<Float> dataPoints;
    private List<String> labels;
    private float maxValue = 100f;
    private int graphType = GRAPH_TYPE_LINE; // 0 = line, 1 = bar

    public static final int GRAPH_TYPE_LINE = 0;
    public static final int GRAPH_TYPE_BAR = 1;

    private int primaryColor;
    private int accentColor;
    private float animationProgress = 1f;

    public WorkoutGraphView(Context context) {
        super(context);
        init(context);
    }

    public WorkoutGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WorkoutGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        dataPoints = new ArrayList<>();
        labels = new ArrayList<>();

        // Get colors from resources
        primaryColor = ContextCompat.getColor(context, R.color.purple);
        accentColor = ContextCompat.getColor(context, R.color.purple_light);

        // Line paint for the graph line
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(primaryColor);
        linePaint.setStrokeWidth(8f);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeJoin(Paint.Join.ROUND);

        // Fill paint for area under the line
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);

        // Grid paint for background lines
        gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        gridPaint.setColor(Color.parseColor("#30FFFFFF"));
        gridPaint.setStrokeWidth(2f);
        gridPaint.setStyle(Paint.Style.STROKE);

        // Text paint for labels
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(32f);
        textPaint.setTextAlign(Paint.Align.CENTER);

        // Bar paint
        barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barPaint.setStyle(Paint.Style.FILL);

        linePath = new Path();
        fillPath = new Path();

        // Sample data for demonstration
        setDataPoints(getSampleData());
        setLabels(getSampleLabels());
    }

    public void setDataPoints(List<Float> data) {
        this.dataPoints = new ArrayList<>(data);
        calculateMaxValue();
        invalidate();
    }

    public void setLabels(List<String> labels) {
        this.labels = new ArrayList<>(labels);
        invalidate();
    }

    public void setGraphType(int type) {
        this.graphType = type;
        invalidate();
    }

    public void setColors(int primaryColor, int accentColor) {
        this.primaryColor = primaryColor;
        this.accentColor = accentColor;
        linePaint.setColor(primaryColor);
        invalidate();
    }

    private void calculateMaxValue() {
        maxValue = 0;
        for (Float value : dataPoints) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        // Add 20% padding to max value
        maxValue *= 1.2f;
        if (maxValue == 0) maxValue = 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (dataPoints.isEmpty()) return;

        int width = getWidth();
        int height = getHeight();
        int padding = 80;
        int bottomPadding = 120;

        int graphWidth = width - (padding * 2);
        int graphHeight = height - padding - bottomPadding;

        // Draw grid lines
        drawGridLines(canvas, padding, graphWidth, graphHeight);

        if (graphType == GRAPH_TYPE_LINE) {
            drawLineGraph(canvas, padding, bottomPadding, graphWidth, graphHeight);
        } else {
            drawBarGraph(canvas, padding, bottomPadding, graphWidth, graphHeight);
        }

        // Draw labels
        drawLabels(canvas, padding, height - bottomPadding + 40, graphWidth);
    }

    private void drawGridLines(Canvas canvas, int padding, int graphWidth, int graphHeight) {
        // Draw horizontal grid lines
        for (int i = 0; i <= 4; i++) {
            float y = padding + (graphHeight * i / 4f);
            canvas.drawLine(padding, y, padding + graphWidth, y, gridPaint);
        }
    }

    private void drawLineGraph(Canvas canvas, int padding, int bottomPadding, int graphWidth, int graphHeight) {
        linePath.reset();
        fillPath.reset();

        int pointCount = dataPoints.size();
        if (pointCount == 0) return;

        float pointSpacing = graphWidth / (float) Math.max(1, pointCount - 1);

        // Create gradient for fill
        int[] colors = {
            Color.argb(180, Color.red(primaryColor), Color.green(primaryColor), Color.blue(primaryColor)),
            Color.argb(60, Color.red(accentColor), Color.green(accentColor), Color.blue(accentColor)),
            Color.argb(0, Color.red(accentColor), Color.green(accentColor), Color.blue(accentColor))
        };
        float[] positions = {0f, 0.6f, 1f};

        LinearGradient gradient = new LinearGradient(
            0, padding,
            0, padding + graphHeight,
            colors, positions,
            Shader.TileMode.CLAMP
        );
        fillPaint.setShader(gradient);

        // Build the path
        for (int i = 0; i < pointCount; i++) {
            float x = padding + (i * pointSpacing);
            float normalizedValue = dataPoints.get(i) / maxValue;
            float y = padding + graphHeight - (normalizedValue * graphHeight * animationProgress);

            if (i == 0) {
                linePath.moveTo(x, y);
                fillPath.moveTo(x, padding + graphHeight);
                fillPath.lineTo(x, y);
            } else {
                // Use quadratic curves for smooth lines
                float prevX = padding + ((i - 1) * pointSpacing);
                float prevNormalizedValue = dataPoints.get(i - 1) / maxValue;
                float prevY = padding + graphHeight - (prevNormalizedValue * graphHeight * animationProgress);

                float controlX = (prevX + x) / 2f;

                linePath.quadTo(controlX, prevY, x, y);
                fillPath.quadTo(controlX, prevY, x, y);
            }

            // Draw data point circles
            canvas.drawCircle(x, y, 12f, linePaint);
            Paint innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            innerCirclePaint.setColor(Color.WHITE);
            canvas.drawCircle(x, y, 6f, innerCirclePaint);
        }

        // Complete fill path
        float lastX = padding + ((pointCount - 1) * pointSpacing);
        fillPath.lineTo(lastX, padding + graphHeight);
        fillPath.close();

        // Draw fill and line
        canvas.drawPath(fillPath, fillPaint);
        canvas.drawPath(linePath, linePaint);
    }

    private void drawBarGraph(Canvas canvas, int padding, int bottomPadding, int graphWidth, int graphHeight) {
        int barCount = dataPoints.size();
        if (barCount == 0) return;

        float totalBarWidth = graphWidth / (float) barCount;
        float barWidth = totalBarWidth * 0.7f;
        float barSpacing = totalBarWidth * 0.3f;

        for (int i = 0; i < barCount; i++) {
            float normalizedValue = dataPoints.get(i) / maxValue;
            float barHeight = normalizedValue * graphHeight * animationProgress;

            float left = padding + (i * totalBarWidth) + (barSpacing / 2f);
            float top = padding + graphHeight - barHeight;
            float right = left + barWidth;
            float bottom = padding + graphHeight;

            // Create gradient for bar
            LinearGradient barGradient = new LinearGradient(
                left, top,
                left, bottom,
                primaryColor,
                accentColor,
                Shader.TileMode.CLAMP
            );
            barPaint.setShader(barGradient);

            // Draw rounded rectangle bar
            RectF barRect = new RectF(left, top, right, bottom);
            canvas.drawRoundRect(barRect, 16f, 16f, barPaint);
        }
    }

    private void drawLabels(Canvas canvas, int padding, float y, int graphWidth) {
        int labelCount = Math.min(labels.size(), dataPoints.size());
        if (labelCount == 0) return;

        float pointSpacing = graphWidth / (float) Math.max(1, labelCount - 1);

        for (int i = 0; i < labelCount; i++) {
            float x = padding + (i * pointSpacing);
            canvas.drawText(labels.get(i), x, y, textPaint);
        }
    }

    private List<Float> getSampleData() {
        List<Float> data = new ArrayList<>();
        data.add(45f);  // Mon
        data.add(60f);  // Tue
        data.add(30f);  // Wed
        data.add(75f);  // Thu
        data.add(50f);  // Fri
        data.add(90f);  // Sat
        data.add(65f);  // Sun
        return data;
    }

    private List<String> getSampleLabels() {
        List<String> sampleLabels = new ArrayList<>();
        sampleLabels.add("Mon");
        sampleLabels.add("Tue");
        sampleLabels.add("Wed");
        sampleLabels.add("Thu");
        sampleLabels.add("Fri");
        sampleLabels.add("Sat");
        sampleLabels.add("Sun");
        return sampleLabels;
    }

    public void animateIn() {
        animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(600)
            .setStartDelay(100)
            .start();
    }
}
