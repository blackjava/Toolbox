package toolbox;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import toolbox.classutilitiestest.NewClass;

public class ClassUtilitiesTest {

    @Test
    public void get_classes_in_non_existing_package() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("springApplicationContext.xml");
        ClassUtilities classUtilities = context.getBean(ClassUtilities.class);
        
        List<Class> classes = classUtilities.getClassesInPackage("toolbox.classutilitiestest.nonexistentpackage");
        assertTrue(classes.isEmpty());
    }
    
    @Test
    public void get_classes_in_package() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("springApplicationContext.xml");
        ClassUtilities classUtilities = context.getBean(ClassUtilities.class);
        
        List<Class> classes = classUtilities.getClassesInPackage("toolbox.classutilitiestest");
        assertEquals(1, classes.size());
        assertEquals(NewClass.class, classes.get(0));
    }
}
