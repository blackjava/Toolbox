package toolbox;

import java.io.Serializable;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import toolbox.classutilitiestest.ExtendingClass;
import toolbox.classutilitiestest.ImplementingClass;
import toolbox.classutilitiestest.NewClass;

public class ClassUtilitiesTest {
    private final ApplicationContext context = new ClassPathXmlApplicationContext("defaultSpringApplicationContext.xml");
    private final ClassUtilities classUtilities = context.getBean(ClassUtilities.class);

    @Test
    public void get_classes_in_non_existing_package() throws Exception {
        List<Class> classes = classUtilities.getClassesInPackage("toolbox.classutilitiestest.nonexistentpackage");
        assertTrue(classes.isEmpty());
    }
    
    @Test
    public void get_classes_in_package() throws Exception {
        List<Class> classes = classUtilities.getClassesInPackage("toolbox.classutilitiestest");
        assertEquals(3, classes.size());
        assertEquals(ExtendingClass.class, classes.get(0));
        assertEquals(ImplementingClass.class, classes.get(1));
        assertEquals(NewClass.class, classes.get(2));
    }

    @Test
    public void get_list_classes_in_package_returns_no_matches() {
        List<Class> classes = classUtilities.getClassesInPackage(List.class, "toolbox.classutilitiestest");
        assertTrue(classes.isEmpty());
    }
    
    @Test
    public void get_serializable_classes_in_package_returns_only_classes_implementing_or_extending_serializable() {
        List<Class> classes = classUtilities.getClassesInPackage(Serializable.class, "toolbox.classutilitiestest");
        assertEquals(2, classes.size());
        assertEquals(ExtendingClass.class, classes.get(0));
        assertEquals(ImplementingClass.class, classes.get(1));
    }

    @Test
    public void get_object_classes_in_package_returns_all_classes() {
        List<Class> classes = classUtilities.getClassesInPackage(Object.class, "toolbox.classutilitiestest");
        assertEquals(3, classes.size());
        assertEquals(ExtendingClass.class, classes.get(0));
        assertEquals(ImplementingClass.class, classes.get(1));
        assertEquals(NewClass.class, classes.get(2));
    }
}
