package executor.service.params;

import executor.service.model.Step;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Queue;
import java.util.stream.Stream;

import static executor.service.params.FileSourcesReader.readFile;
import static java.util.Arrays.asList;

public class ExecutionsServiceArgumentsProvider implements ArgumentsProvider {
    private static final String filename = "json/ExecutionServiceTestData.json";
    @Override
    public Stream<? extends Arguments> provideArguments(
            final ExtensionContext context) {
        return Stream.of(Arguments.of(asList(
                readFile(filename, Step[].class))));
    }

//    static Stream<Arguments> testExecutionService(){
//        Queue<>
//    }
}
