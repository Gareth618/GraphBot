package model;

public enum Color {
    WHITE(0xffffff),
    BLACK(0x000000),
    RED(0xff0000),
    GREEN(0x00ff00);

    private final int hex;

    Color(int hex) {
        this.hex = hex;
    }

    public java.awt.Color getColor() {
        return new java.awt.Color(hex);
    }
}
