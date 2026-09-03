package pl.serwersms.apiv2;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;

public class SkipReasonListener implements TestExecutionListener {

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (testIdentifier.isTest() && testExecutionResult.getStatus() == TestExecutionResult.Status.ABORTED) {
            String reason = testExecutionResult.getThrowable()
                    .map(Throwable::getMessage)
                    .orElse("no reason provided");

            System.err.println("[SKIPPED] " +  testIdentifier.getDisplayName() + " from " + className(testIdentifier) + ". " + reason);
        }
    }

    private String className(TestIdentifier testIdentifier) {
        return testIdentifier.getSource()
                .filter(source -> source instanceof MethodSource)
                .map(source -> ((MethodSource) source).getClassName())
                .orElse("unknown");
    }
}
