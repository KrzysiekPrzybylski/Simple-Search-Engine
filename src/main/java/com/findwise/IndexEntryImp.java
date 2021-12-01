package com.findwise;

public class IndexEntryImp implements IndexEntry {

    private String docId;
    private double tfIdf;

    public IndexEntryImp(String docId, double tfIdf) {
        this.docId = docId;
        this.tfIdf = tfIdf;
    }
    public IndexEntryImp(IndexEntry indexEntry) {
        this.docId = indexEntry.getId();
        this.tfIdf = indexEntry.getScore();
    }

    @Override
    public String getId() {
        return docId;
    }

    @Override
    public void setId(String id) {

    }

    @Override
    public double getScore() {
        return tfIdf;
    }

    @Override
    public void setScore(double tfIdf) {

    }
}
