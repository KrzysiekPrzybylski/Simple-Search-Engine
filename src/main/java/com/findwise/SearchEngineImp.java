package com.findwise;

import java.util.*;
import java.util.stream.Collectors;

public class SearchEngineImp implements SearchEngine{

    private final HashMap<String, String> docs = new HashMap<>();
    private final HashMap<String, List<IndexEntry>> indexEntriesMap = new HashMap<>();



    @Override
    public void indexDocument(String id, String content) {

        docs.put(id,content);
        String[] tokens = getTokens(content);

        Map<String, Integer> wordFrequencyMap = getWordFrequency(tokens);

        for(String entryWord : wordFrequencyMap.keySet()) {

            IndexEntry entry = new IndexEntryImp(id, (double) wordFrequencyMap.get(entryWord)/ tokens.length);

            if (indexEntriesMap.containsKey(entryWord)) {
                indexEntriesMap.get(entryWord).add(entry);
            } else {
                indexEntriesMap.put(entryWord, new ArrayList<>(List.of(entry)));
            }
        }
    }

    @Override
    public List<IndexEntry> search(String term) {

        String rawTerm = term.replace(" ","").toLowerCase();

        List<IndexEntry> indexEntries = this.indexEntriesMap.getOrDefault(rawTerm, Collections.emptyList());

        return indexEntries
                .stream()
                .map(IndexEntryImp :: new)
                .peek(entry -> entry.setScore(entry.getScore() * getIDF(term)))
                .sorted(Comparator.comparing(IndexEntry:: getScore).reversed())
                .collect(Collectors.toList());
    }

    public String[] getTokens(String content) {
        return content.toLowerCase().split("\\s+");
    }

    public Map<String, Integer> getWordFrequency(String[] words) {
        HashMap<String, Integer> wordFrequency = new HashMap<>();

        for(String w: words) {

            if(!wordFrequency.containsKey(w)) {
                wordFrequency.put(w,1);
            } else {
                wordFrequency.put(w, wordFrequency.get(w) + 1);
            }
        }
        return wordFrequency;
    }

    public HashMap<String ,String> getDocs() {
        return docs;

    }
    public HashMap<String, List<IndexEntry>> getIndexEntriesMap() {
        return indexEntriesMap;
    }
    public double getIDF(String term) {

        int documentTermCount = getIndexEntriesMap().getOrDefault(term, Collections.emptyList()).size();

        return Math.log((double) getDocs().size()/documentTermCount + 1);
    }
}
