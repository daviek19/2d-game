package game_object;

public class Door extends GameObject {

    public Door() {
        name = "Door";
        setImage("/objects/door.png");
        collision = true;
    }
}
