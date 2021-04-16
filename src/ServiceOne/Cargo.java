package ServiceOne;

public class Cargo
{
    public enum TypeOfCargo
    {
        LOOSE, LIQUID, CONTAINER
    }

    @Override
    public String toString() {
        switch (this.type)
        {
            case CONTAINER -> {
                return "Container";
            }
            case LOOSE -> {
                return "Loose";
            }
            case LIQUID -> {
                return "Liquid";
            }
            default -> throw new IllegalArgumentException("Invalid cargo");
        }
    }

    public void setType(TypeOfCargo type) {
        this.type = type;
    }

    public TypeOfCargo getType() {
        return type;
    }

    private TypeOfCargo type;
}
