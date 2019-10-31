package com.fronke.betatransmit.extratorrentApi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by front on 31/10/2016.
 */

public class ExtratorrentApi {

    private String mTorrentHostPt1 = "https://extratorrent.cd/search/?search=";
    private String mTorrentHostPt2 = "&pp=&srt=seeds&order=desc";

    public List<String> getTorrentLinks(String episodeName, String[] searchTerms) {

        List<String> foundMagnetLinks = new ArrayList<>();

        // Remove ( )
        int open = episodeName.indexOf('(');
        int close = episodeName.indexOf(')');
        if (open != -1 && close != -1) {
            episodeName = episodeName.substring(0, open) + episodeName.substring(close + 1, episodeName.length());
        }

        for (int i=0 ; i < searchTerms.length ; i++) {
            String search = episodeName + " " + searchTerms[i];
            search = search.replaceAll(" ", "%20");
            String url = mTorrentHostPt1 + search + mTorrentHostPt2;
            foundMagnetLinks.addAll(extratorrentMagnetSearch(url));
        }

        return foundMagnetLinks;
    }

    public List<String> extratorrentMagnetSearch(String searchUrl) {

        List<String> links = new ArrayList<>();
        int foundLinks = 0;
        int indexLinks = 0;

        try {
            Document htmlPage = Jsoup.connect(searchUrl).get();
            Elements elements = htmlPage.select("table.tl tr td a");

            while (foundLinks < 2 && indexLinks < elements.size()) {

                String link = elements.get(indexLinks).attr("href");

                if (link.startsWith("magnet:?")) {
                    links.add(link);
                    foundLinks++;
                }

                indexLinks++;
            }

        } catch (Exception e) {}

        return links;
    }

}
