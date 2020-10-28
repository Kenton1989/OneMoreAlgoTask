import java.util.ArrayList;
import java.util.List;

public class TestRound {
    private List<Test> tests = new ArrayList<>();

    TestRound() {

    }

    public void addTest(Test test) {
        tests.add(test);
    }

    public List<TestResult> runAllTest() {
        List<TestResult> results = new ArrayList<>(tests.size());

        for (Test test: tests) {
            results.add(test.run());
        }

        return results;
    }

    public void printResults(Iterable<TestResult> results) {
        System.out.println(TestResult.TABLE_HEADER);
        for(TestResult result: results) {
            System.out.println(result);
        }
    }

    public void printTests() {
        
    }
}
