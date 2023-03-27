package sixman.helfit.restdocs.support;

import lombok.RequiredArgsConstructor;

public interface DocumentLinkGenerator {
    static String generateLinkCode(DocUrl docUrl) {
        return String.format("link:common/%s.html[%s %s,role=\"popup\"]", docUrl.pageId, docUrl.text, "코드");
    }

    static String generateText(DocUrl docUrl) {
        return String.format("%s %s", docUrl.text, "코드명");
    }

    @RequiredArgsConstructor
    enum DocUrl {
        USER_STATUS("user-status", "회원 상태")
        ;

        private final String pageId;
        private final String text;
    }
}
