package toolbox;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import toolbox.classutilitiestest.NewClass;

public class ClassUtilitiesTest {
    private ApplicationContext context = new ClassPathXmlApplicationContext("defaultSpringApplicationContext.xml");
    private ClassUtilities classUtilities = context.getBean(ClassUtilities.class);

    @Test
    public void get_classes_in_non_existing_package() throws Exception {
        List<Class> classes = classUtilities.getClassesInPackage("toolbox.classutilitiestest.nonexistentpackage");
        assertTrue(classes.isEmpty());
    }
    
    @Test
    public void get_classes_in_package() throws Exception {
        List<Class> classes = classUtilities.getClassesInPackage("toolbox.classutilitiestest");
        assertEquals(1, classes.size());
        assertEquals(NewClass.class, classes.get(0));
    }
}
