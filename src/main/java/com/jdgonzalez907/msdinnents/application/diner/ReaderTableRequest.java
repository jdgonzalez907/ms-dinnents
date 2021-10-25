package com.jdgonzalez907.msdinnents.application.diner;

import com.jdgonzalez907.msdinnents.shared.application.Reader;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ReaderTableRequest implements Reader<String, List<DinerRequest>> {

    private final Predicate<String> conditionTableName = s -> s.startsWith("<") && s.endsWith(">");

    @Override
    public List<DinerRequest> transform(String input) {
        return this.process(Arrays.asList(input.split("\n")), Collections.emptyList());
    }

    private List<DinerRequest> process(List<String> inputList, List<DinerRequest> dinerRequestsProcessed) {
        Optional<DinerRequest> tableRequest = inputList.stream().takeWhile(conditionTableName).findFirst().map(name -> new DinerRequest(
                name,
                inputList.stream()
                        .skip(1)
                        .dropWhile(conditionTableName)
                        .takeWhile(conditionTableName.negate())
                        .map(filter -> Map.entry(
                                DinerRequest.Filter.valueOf(filter.substring(0, filter.indexOf(":"))),
                                filter.substring(filter.indexOf(":") + 1)
                        )).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        ));

        if (tableRequest.isEmpty()) {
            return dinerRequestsProcessed;
        } else {
            var x = inputList.stream().skip(1)
                    .dropWhile(conditionTableName.negate())
                    .collect(Collectors.toList());
            return process(x,
                    Stream.concat(dinerRequestsProcessed.stream(),
                                    tableRequest.map(Collections::singletonList).orElse(Collections.emptyList()).stream())
                            .collect(Collectors.toList()));
        }
    }
}
