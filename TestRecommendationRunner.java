public class TestRecommendationRunner {
    public static void main(String[] args) {
        RecommendationRunner runner = new RecommendationRunner();
        runner.getItemsToRate();
        runner.printRecommendationsFor("65");

    }
}
