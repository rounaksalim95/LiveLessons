package livelessons.imagestreamgang.utils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

/**
 * Helpful methods for manipulating various Java 8 Streams features.
 */
public class StreamsUtils {
    /**
     * A utility class should always define a private constructor.
     */
    private StreamsUtils() {
    }

    /**
     * Waits for all of the CompletableFutures in @a futures to finish
     * and then returns a CompletableFuture containing a List with all
     * the results.
     * @param futures A list of completable futures.
     */
    public static <T> CompletableFuture<List<T>> joinAll(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return allDoneFuture.thenApply(v ->
                                       futures.stream()
                                       .map(CompletableFuture::join)
                                       .collect(toList()));
    }

    /**
     * A generic negation predicate that can be used to negate the
     * return value of urlCached (used by Collection.filter() calls).
     *
     * @return The negation of the input predicate.
     */
    public static<T> Predicate<T> not(Predicate<T> p) {
        return t -> !p.test(t);
    }
}
