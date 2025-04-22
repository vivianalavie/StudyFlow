/**
 * 
 */
/**
 * 
 */
module StudyFlow {
    requires java.net.http;
    requires java.base; // nicht zwingend nötig, wird implizit geladen
    requires com.fasterxml.jackson.databind; // nur falls du Jackson benutzt

    exports auth;
    exports models;
    exports utils;
}
