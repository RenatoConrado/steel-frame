package io.github.renatoconrado.steel_frame;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class SteelFrameApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void writeDocumentationSnippets() {
        var verifyModules = ApplicationModules.of(SteelFrameApplication.class).verify();

        new Documenter(verifyModules)
            .writeModulesAsPlantUml()
            .writeIndividualModulesAsPlantUml();
    }

}
