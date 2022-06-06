package model;

public enum Color {
    WHITE(0xffffff),
    BLACK(0x333333),
    YELLOW(0xffd700),
    ORANGE(0xffa500),
    RED(0xff4500);

    private final int hex;

    Color(int hex) {
        this.hex = hex;
    }

    public java.awt.Color getColor() {
        return new java.awt.Color(hex);
    }
}
