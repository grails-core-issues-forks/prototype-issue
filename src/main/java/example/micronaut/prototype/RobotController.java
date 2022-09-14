package example.micronaut.prototype;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.Arrays;
import java.util.List;

@Controller("/prototype") // <1>
public class RobotController {

    private final RobotFather father;
    private final RobotFather mother;

    public RobotController(RobotFather father,  // <2>
                           RobotFather mother) {
        this.father = father;
        this.mother = mother;
    }

    @Get // <3>
    List<String> children() {
        return Arrays.asList(
                father.child().getSerialNumber(),
                mother.child().getSerialNumber()
        );
    }
}
