package com.example.ser531;

public class Queries {

    public String CURRENT_LAT;
    public String CURRENT_LONG;
    public String KEYWORD;
    public String URL;

    public Queries(String latitude, String longitude) {
        this.CURRENT_LAT = latitude;
        this.CURRENT_LONG = longitude;
    }

    public Queries(String URL) {
        this.URL = URL;
    }
    public String nearestPOI() {
        String query = "PREFIX poi: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-2#>\n" +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX spatialF: <http://jena.apache.org/spatial#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX geof: <http://www.opengis.net/def/function/geosparql/>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "SELECT ?subject ?lat ?long ?name\n" +
                "WHERE {\n" +
                "?subject geo:lat ?lat .\n" +
                "?subject geo:long ?long .\n" +
                "?subject poi:hasName ?name.\n" +
                "  BIND(xsd:double(?lat) - xsd:double(" + CURRENT_LAT + ") AS ?first) .\n" +
                "  BIND(?first * ?first AS ?firstSQ) .\n" +
                "  BIND(xsd:double(?long) - xsd:double(" + CURRENT_LONG + ") AS ?second) .\n" +
                "  BIND(?second * ?second AS ?secondSQ) .\n" +
                "  BIND(?firstSQ + ?secondSQ AS ?distance) .\n" +
                "}\n" +
                "ORDER BY (?distance)\n" +
                "LIMIT 20\n";
        return query;
    }

    public String nearestPubs() {
        String query = "PREFIX poi: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-2#>\n" +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX pub: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-14#>\n" +
                "PREFIX station: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-8#>\n" +
                "PREFIX st: <http://ns.inria.fr/sparql-template/>\n" +
                "PREFIX po: <http://purl.org/ontology/po/>\n" +
                "\n" +
                "SELECT ?subject ?lat ?long ?name\n" +
                "WHERE {\n" +
                "  \tSERVICE <http://ec2-52-86-45-247.compute-1.amazonaws.com:3030/PubsDataset> {\n" +
                "  \t?subject rdf:type pub:Pub .\n" +
                "    ?subject geo:lat ?lat .\n" +
                "    ?subject geo:long ?long .\n" +
                "?subject pub:hasName ?name.\n" +
                "  BIND(xsd:double(?lat) - xsd:double(" + CURRENT_LAT + ") AS ?first) .\n" +
                "  BIND(?first * ?first AS ?firstSQ) .\n" +
                "  BIND(xsd:double(?long) - xsd:double(" + CURRENT_LONG + ") AS ?second) .\n" +
                "  BIND(?second * ?second AS ?secondSQ) .\n" +
                "  BIND(?firstSQ + ?secondSQ AS ?distance) .\n" +
                "  } \n" +
                "}\n" +
                "ORDER BY ?distance \n" +
                "LIMIT 20\n";
        return query;
    }

    public String nearestStations() {
        String query = "PREFIX poi: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-2#>\n" +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX pub: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-14#>\n" +
                "PREFIX station: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-8#>\n" +
                "PREFIX st: <http://ns.inria.fr/sparql-template/>\n" +
                "PREFIX po: <http://purl.org/ontology/po/>\n" +
                "\n" +
                "SELECT ?subject ?lat ?long ?name\n" +
                "WHERE {\n" +
                "  \tSERVICE <http://ec2-54-224-109-188.compute-1.amazonaws.com:3030/StationsDataset> {\n" +
                "  \t?subject rdf:type station:TubeStation .\n" +
                "    ?subject geo:lat ?lat .\n" +
                "    ?subject geo:long ?long .\n" +
                "?subject station:hasName ?name.\n" +
                "  BIND(xsd:double(?lat) - xsd:double(" + CURRENT_LAT + ") AS ?first) .\n" +
                "  \tBIND(?first * ?first AS ?firstSQ) .\n" +
                "  BIND(xsd:double(?long) - xsd:double(" + CURRENT_LONG + ") AS ?second) .\n" +
                "  \tBIND(?second * ?second AS ?secondSQ) .\n" +
                "  \tBIND(?firstSQ + ?secondSQ AS ?distance) .\n" +
                "  } \n" +
                "}\n" +
                "ORDER BY ?distance \n" +
                "LIMIT 20\n";
        return query;
    }

    /*public static String nearestCovidSafePOI = "PREFIX poi: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-2#>\n" +
            "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
            "PREFIX spatialF: <http://jena.apache.org/spatial#>\n" +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
            "PREFIX geof: <http://www.opengis.net/def/function/geosparql/>\n" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "SELECT ?distance ?subject\n" +
            "WHERE {\n" +
            "?subject geo:lat ?lat .\n" +
            "?subject geo:long ?long .\n" +
            "?subject poi:hasCovidSafetyRating ?rating .\n" +
            "  BIND(xsd:double(?lat) - xsd:double(" + CURRENT_LAT + ") AS ?first) .\n" +
            "  BIND(?first * ?first AS ?firstSQ) .\n" +
            "  BIND(xsd:double(?long) - xsd:double(" + CURRENT_LONG + ") AS ?second) .\n" +
            "  BIND(?second * ?second AS ?secondSQ) .\n" +
            "  BIND(?firstSQ + ?secondSQ AS ?distance) .\n" +
            "FILTER(xsd:int(?rating) > 4).\n" +
            "}\n" +
            "ORDER BY (?distance)\n" +
            "LIMIT 20\n";*/

    public String nearestCovidSafeFederated() {
        String query = "PREFIX poi: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-2#>\n" +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX pub: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-14#>\n" +
                "PREFIX station: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-8#>\n" +
                "PREFIX st: <http://ns.inria.fr/sparql-template/>\n" +
                "PREFIX po: <http://purl.org/ontology/po/>\n" +
                "\n" +
                "SELECT ?subject ?lat ?long ?name\n" +
                "WHERE {\n" +
                "\t{\n" +
                "      ?subject geo:lat ?lat .\n" +
                "      ?subject geo:long ?long .\n" +
                "?subject poi:hasName ?name.\n" +
                "      BIND(xsd:double(?lat) - xsd:double(" + CURRENT_LAT + ") AS ?first) .\n" +
                "      BIND(?first * ?first AS ?firstSQ) .\n" +
                "      BIND(xsd:double(?long) - xsd:double(" + CURRENT_LONG + ") AS ?second) .\n" +
                "      BIND(?second * ?second AS ?secondSQ) .\n" +
                "      BIND(?firstSQ + ?secondSQ AS ?distance) .\n" +
                "\t  ?subject poi:hasCovidSafetyRating ?rating .\n" +
                "      FILTER(xsd:int(?rating) > 4).    \n" +
                "  }\n" +
                "  UNION\n" +
                "  {\n" +
                "\tSERVICE <http://ec2-52-86-45-247.compute-1.amazonaws.com:3030/PubsDataset> {\n" +
                "    \t?subject geo:lat ?lat .\n" +
                "        ?subject geo:long ?long .\n" +
                "?subject pub:hasName ?name.\n" +
                "        BIND(xsd:double(?lat) - xsd:double(" + CURRENT_LAT + ") AS ?first) .\n" +
                "        BIND(?first * ?first AS ?firstSQ) .\n" +
                "        BIND(xsd:double(?long) - xsd:double(" + CURRENT_LONG + ") AS ?second) .\n" +
                "        BIND(?second * ?second AS ?secondSQ) .\n" +
                "        BIND(?firstSQ + ?secondSQ AS ?distance) .\n" +
                "        ?subject pub:hasCovidSafetyRating ?rating .\n" +
                "  \t\tFILTER(xsd:int(?rating) > 4).\n" +
                "    }\n" +
                "  }\n" +
                "  UNION\n" +
                "  {\n" +
                "\tSERVICE <http://ec2-54-224-109-188.compute-1.amazonaws.com:3030/StationsDataset> {\n" +
                "    \t?subject geo:lat ?lat .\n" +
                "        ?subject geo:long ?long .\n" +
                "?subject station:hasName ?name.\n" +
                "        BIND(xsd:double(?lat) - xsd:double(" + CURRENT_LAT + ") AS ?first) .\n" +
                "        BIND(?first * ?first AS ?firstSQ) .\n" +
                "        BIND(xsd:double(?long) - xsd:double(" + CURRENT_LONG + ") AS ?second) .\n" +
                "        BIND(?second * ?second AS ?secondSQ) .\n" +
                "        BIND(?firstSQ + ?secondSQ AS ?distance) .\n" +
                "        ?subject station:hasCovidSafetyRating ?rating .\n" +
                "  \t\tFILTER(xsd:int(?rating) > 4).\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "ORDER BY ?distance ?rating\n" +
                "LIMIT 50\n";
        return query;
    }


    /*public static String nearestPopularPOI = "PREFIX poi: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-2#>\n" +
            "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
            "PREFIX spatialF: <http://jena.apache.org/spatial#>\n" +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
            "PREFIX geof: <http://www.opengis.net/def/function/geosparql/>\n" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "\n" +
            "SELECT ?distance ?subject \n" +
            "WHERE {\n" +
            "    ?subject geo:lat ?lat .\n" +
            "    ?subject geo:long ?long .\n" +
            "  \tBIND(xsd:double(?lat) - xsd:double(51.556) AS ?first) .\n" +
            "  \tBIND(?first * ?first AS ?firstSQ) .\n" +
            "  \tBIND(xsd:double(?long) - xsd:double(-0.336) AS ?second) .\n" +
            "  \tBIND(?second * ?second AS ?secondSQ) .\n" +
            "  \tBIND(?firstSQ + ?secondSQ AS ?distance) .\n" +
            "  \t?subject poi:hasPopularityRating ?rating .\n" +
            "  \tFILTER(xsd:int(?rating) > 8).\n" +
            "}\n" +
            "ORDER BY ?distance ?rating\n" +
            "LIMIT 20\n";*/


    /*public static String covidSafePopularNearestPOI = "PREFIX poi: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-2#>\n" +
            "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
            "PREFIX spatialF: <http://jena.apache.org/spatial#>\n" +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
            "PREFIX geof: <http://www.opengis.net/def/function/geosparql/>\n" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "\n" +
            "SELECT ?distance ?subject\n" +
            "WHERE {\n" +
            "    ?subject geo:lat ?lat .\n" +
            "    ?subject geo:long ?long .\n" +
            "  \tBIND(xsd:double(?lat) - xsd:double(51.556) AS ?first) .\n" +
            "  \tBIND(?first * ?first AS ?firstSQ) .\n" +
            "  \tBIND(xsd:double(?long) - xsd:double(-0.336) AS ?second) .\n" +
            "  \tBIND(?second * ?second AS ?secondSQ) .\n" +
            "  \tBIND(?firstSQ + ?secondSQ AS ?distance) .\n" +
            "  \t?subject poi:hasCovidSafetyRating ?covidRating .\n" +
            "  \t?subject poi:hasPopularityRating ?popularRating .\n" +
            "  FILTER(xsd:int(?covidRating) > 4 && xsd:int(?popularRating) > 8).\n" +
            "}\n" +
            "ORDER BY ?distance ?covidRating ?popularRating\n" +
            "LIMIT 20\n";
*/


    public String KeyWordSearchfederated() {
        String query = "PREFIX poi: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-2#>\n" +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX pub: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-14#>\n" +
                "PREFIX station: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-8#>\n" +
                "PREFIX st: <http://ns.inria.fr/sparql-template/>\n" +
                "\n" +
                "SELECT ?subject ?lat ?long ?name\n" +
                "WHERE {\n" +
                "\t{\n" +
                "      ?subject rdf:type poi:POI.\n" +
                "      ?subject poi:hasName ?name.\n" +
                "      ?subject geo:lat ?lat.\n" +
                "      ?subject geo:long ?long.\n" +
                "\t}\n" +
                "\n" +
                "\tUNION\n" +
                "\t{\n" +
                "\t\tSERVICE <http://ec2-52-86-45-247.compute-1.amazonaws.com:3030/dataset2>{\n" +
                "    \t\t?subject rdf:type pub:Pub .\n" +
                "      \t\t?subject pub:hasName ?name.\n" +
                "      ?subject geo:lat ?lat.\n" +
                "      ?subject geo:long ?long.\n" +
                "      \t\t\n" +
                "  \t\t}\n" +
                "\t}\n" +
                "  \tUNION\n" +
                "  \t{\n" +
                "    \tSERVICE <http://ec2-54-224-109-188.compute-1.amazonaws.com:3030/StationsDataset>{\n" +
                "    \t\t?subject rdf:type station:TubeStation .\n" +
                "      \t\t?subject station:hasName ?name.\n" +
                "      ?subject geo:lat ?lat.\n" +
                "      ?subject geo:long ?long.\n" +
                "  \t\t}\n" +
                "  \t}\n" +
                "  \tFILTER(contains(?name, \"" + KEYWORD + "\")).\n" +

                "}\n";
        return query;
    }


    public String nearestFederated() {
        System.out.println(CURRENT_LONG);
        String query = "PREFIX poi: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-2#>\n" +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX pub: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-14#>\n" +
                "PREFIX station: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-8#>\n" +
                "PREFIX st: <http://ns.inria.fr/sparql-template/>\n" +
                "\n" +
                "SELECT ?subject ?lat ?long ?name\n" +
                "WHERE {\n" +
                "\t{\n" +
                "      ?subject geo:lat ?lat .\n" +
                "      ?subject geo:long ?long .\n" +
                "?subject poi:hasName ?name.\n" +
                "      BIND(xsd:double(?lat) - xsd:double(" + CURRENT_LAT + ") AS ?first) .\n" +
                "      BIND(?first * ?first AS ?firstSQ) .\n" +
                "      BIND(xsd:double(?long) - xsd:double(" + CURRENT_LONG + ") AS ?second) .\n" +
                "      BIND(?second * ?second AS ?secondSQ) .\n" +
                "      BIND(?firstSQ + ?secondSQ AS ?distance) .\n" +
                "  }\n" +
                "  UNION\n" +
                "  {\n" +
                "\tSERVICE <http://ec2-52-86-45-247.compute-1.amazonaws.com:3030/PubsDataset> {\n" +
                "    \t?subject geo:lat ?lat .\n" +
                "        ?subject geo:long ?long .\n" +
                "?subject pub:hasName ?name.\n" +
                "        BIND(xsd:double(?lat) - xsd:double(" + CURRENT_LAT + ") AS ?first) .\n" +
                "        BIND(?first * ?first AS ?firstSQ) .\n" +
                "        BIND(xsd:double(?long) - xsd:double(" + CURRENT_LONG + ") AS ?second) .\n" +
                "        BIND(?second * ?second AS ?secondSQ) .\n" +
                "        BIND(?firstSQ + ?secondSQ AS ?distance) .\n" +
                "    }\n" +
                "  }\n" +
                "  UNION\n" +
                "  {\n" +
                "\tSERVICE <http://ec2-54-224-109-188.compute-1.amazonaws.com:3030/StationsDataset> {\n" +
                "    \t?subject geo:lat ?lat .\n" +
                "        ?subject geo:long ?long .\n" +
                "?subject station:hasName ?name.\n" +
                "        BIND(xsd:double(?lat) - xsd:double(" + CURRENT_LAT + ") AS ?first) .\n" +
                "        BIND(?first * ?first AS ?firstSQ) .\n" +
                "        BIND(xsd:double(?long) - xsd:double(" + CURRENT_LONG + ") AS ?second) .\n" +
                "        BIND(?second * ?second AS ?secondSQ) .\n" +
                "        BIND(?firstSQ + ?secondSQ AS ?distance) .\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "ORDER BY ?distance\n" +
                "LIMIT 50\n";
        return query;
    }


    /*public static String latLongPubs = "PREFIX pubs: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-14#>\n" +
            "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
            "SELECT ?subject ?lat ?long\n" +
            "WHERE {\n" +
            "  ?subject geo:lat ?lat.\n" +
            "  ?subject geo:long ?long\n" +
            "}\n" +
            "LIMIT 25";*/


    /*public static String allProperties = "PREFIX spatial:<http://jena.apache.org/spatial#>\n" +
            "PREFIX pubs: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-14#>\n" +
            "\n" +
            "SELECT ?predicate\n" +
            "WHERE {\n" +
            "  ?subject ?predicate ?object.\n" +
            "}";*/


    public String nearestPopularFederated() {
        String query = "PREFIX poi: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-2#>\n" +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX pub: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-14#>\n" +
                "PREFIX station: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-8#>\n" +
                "PREFIX st: <http://ns.inria.fr/sparql-template/>\n" +
                "PREFIX po: <http://purl.org/ontology/po/>\n" +
                "\n" +
                "SELECT ?subject ?lat ?long ?name\n" +
                "WHERE {\n" +
                "\t{\n" +
                "      ?subject geo:lat ?lat .\n" +
                "      ?subject geo:long ?long .\n" +
                "?subject poi:hasName ?name.\n" +
                "      BIND(xsd:double(?lat) - xsd:double(" + CURRENT_LAT + ") AS ?first) .\n" +
                "      BIND(?first * ?first AS ?firstSQ) .\n" +
                "      BIND(xsd:double(?long) - xsd:double(" + CURRENT_LONG + ") AS ?second) .\n" +
                "      BIND(?second * ?second AS ?secondSQ) .\n" +
                "      BIND(?firstSQ + ?secondSQ AS ?distance) .\n" +
                "\t  ?subject poi:hasPopularityRating ?rating .\n" +
                "      FILTER(xsd:int(?rating) > 8).    \n" +
                "  }\n" +
                "  UNION\n" +
                "  {\n" +
                "\tSERVICE <http://ec2-52-86-45-247.compute-1.amazonaws.com:3030/PubsDataset> {\n" +
                "    \t?subject geo:lat ?lat .\n" +
                "        ?subject geo:long ?long .\n" +
                "?subject pub:hasName ?name.\n" +
                "        BIND(xsd:double(?lat) - xsd:double(" + CURRENT_LAT + ") AS ?first) .\n" +
                "        BIND(?first * ?first AS ?firstSQ) .\n" +
                "        BIND(xsd:double(?long) - xsd:double(" + CURRENT_LONG + ") AS ?second) .\n" +
                "        BIND(?second * ?second AS ?secondSQ) .\n" +
                "        BIND(?firstSQ + ?secondSQ AS ?distance) .\n" +
                "        ?subject pub:hasPopularityRating ?rating .\n" +
                "  \t\tFILTER(xsd:int(?rating) > 8).\n" +
                "    }\n" +
                "  }\n" +
                "  UNION\n" +
                "  {\n" +
                "\tSERVICE <http://ec2-54-224-109-188.compute-1.amazonaws.com:3030/StationsDataset> {\n" +
                "    \t?subject geo:lat ?lat .\n" +
                "        ?subject geo:long ?long .\n" +
                "?subject station:hasName ?name.\n" +
                "        BIND(xsd:double(?lat) - xsd:double(" + CURRENT_LAT + ") AS ?first) .\n" +
                "        BIND(?first * ?first AS ?firstSQ) .\n" +
                "        BIND(xsd:double(?long) - xsd:double(" + CURRENT_LONG + ") AS ?second) .\n" +
                "        BIND(?second * ?second AS ?secondSQ) .\n" +
                "        BIND(?firstSQ + ?secondSQ AS ?distance) .\n" +
                "        ?subject station:hasPopularityRating ?rating .\n" +
                "  \t\tFILTER(xsd:int(?rating) > 8).\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "ORDER BY ?distance ?rating\n" +
                "LIMIT 50\n";
        return query;
    }


    public String nearestCovidSafePopularFederated() {
        String query = "PREFIX poi: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-2#>\n" +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX pub: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-14#>\n" +
                "PREFIX station: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-8#>\n" +
                "PREFIX st: <http://ns.inria.fr/sparql-template/>\n" +
                "PREFIX po: <http://purl.org/ontology/po/>\n" +
                "\n" +
                "SELECT ?subject ?lat ?long ?name ?type\n" +
                "WHERE {\n" +
                "\t{\n" +
                "      ?subject geo:lat ?lat .\n" +
                "      ?subject geo:long ?long .\n" +
                "?subject poi:hasName ?name.\n" +
                "      BIND(xsd:double(?lat) - xsd:double(" + CURRENT_LAT + ") AS ?first) .\n" +
                "      BIND(?first * ?first AS ?firstSQ) .\n" +
                "      BIND(xsd:double(?long) - xsd:double(" + CURRENT_LONG + ") AS ?second) .\n" +
                "      BIND(?second * ?second AS ?secondSQ) .\n" +
                "      BIND(?firstSQ + ?secondSQ AS ?distance) .\n" +
                "\t  ?subject poi:hasCovidSafetyRating ?covidRating .\n" +
                "  \t  ?subject poi:hasPopularityRating ?popularRating .\n" +
                "      FILTER(xsd:int(?covidRating) > 4 && xsd:int(?popularRating) > 8).\n" +
                "  }\n" +
                "  UNION\n" +
                "  {\n" +
                "\tSERVICE <http://ec2-52-86-45-247.compute-1.amazonaws.com:3030/PubsDataset> {\n" +
                "    \t?subject geo:lat ?lat .\n" +
                "        ?subject geo:long ?long .\n" +
                "?subject pub:hasName ?name.\n" +
                "        BIND(xsd:double(?lat) - xsd:double(" + CURRENT_LAT + ") AS ?first) .\n" +
                "        BIND(?first * ?first AS ?firstSQ) .\n" +
                "        BIND(xsd:double(?long) - xsd:double(" + CURRENT_LONG + ") AS ?second) .\n" +
                "        BIND(?second * ?second AS ?secondSQ) .\n" +
                "        BIND(?firstSQ + ?secondSQ AS ?distance) .\n" +
                "        ?subject pub:hasCovidSafetyRating ?covidRating .\n" +
                "  \t    ?subject pub:hasPopularityRating ?popularRating .\n" +
                "        FILTER(xsd:int(?covidRating) > 4 && xsd:int(?popularRating) > 8).\n" +
                "    }\n" +
                "  }\n" +
                "  UNION\n" +
                "  {\n" +
                "\tSERVICE <http://ec2-54-224-109-188.compute-1.amazonaws.com:3030/StationsDataset> {\n" +
                "    \t?subject geo:lat ?lat .\n" +
                "        ?subject geo:long ?long .\n" +
                "?subject station:hasName ?name.\n" +
                "        BIND(xsd:double(?lat) - xsd:double(" + CURRENT_LAT + ") AS ?first) .\n" +
                "        BIND(?first * ?first AS ?firstSQ) .\n" +
                "        BIND(xsd:double(?long) - xsd:double(" + CURRENT_LONG + ") AS ?second) .\n" +
                "        BIND(?second * ?second AS ?secondSQ) .\n" +
                "        BIND(?firstSQ + ?secondSQ AS ?distance) .\n" +
                "        ?subject station:hasCovidSafetyRating ?covidRating .\n" +
                "  \t    ?subject station:hasPopularityRating ?popularRating .\n" +
                "        FILTER(xsd:int(?covidRating) > 4 && xsd:int(?popularRating) > 8).\n" +
                "\n" +
                "    }\n" +
                "  }\n" +
                "}\n" +
                "ORDER BY ?distance ?covidRating ?popularRating\n" +
                "LIMIT 50\n";
        return query;
    }

    public String getPOIDetailsQuery() {
        String detailsPOI = "PREFIX poi: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-2#>\n" +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX pub: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-14#>\n" +
                "PREFIX station: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-8#>\n" +
                "\n" +
                "SELECT ?name ?address ?website ?reviewCount ?polarityCount ?popularityRating ?covidRating\n" +
                "WHERE {\n" +
                "  <"+ URL +"> poi:hasName ?name .\n" +
                "  <"+ URL +"> poi:hasAddress ?address .\n" +
                "  OPTIONAL { <"+ URL +"> poi:hasWebsite ?website }.\n" +
                "  <"+ URL +"> poi:hasReviewCount ?reviewCount .\n" +
                "  <"+ URL +"> poi:hasPolarity ?polarityCount .\n" +
                "  <"+ URL +"> poi:hasPopularityRating ?popularityRating .\n" +
                "  <"+ URL +"> poi:hasCovidSafetyRating ?covidRating .\n" +
                "}\n" +
                "LIMIT 50\n";
        return detailsPOI;

    }

    public String getPubsDetailsQuery() {
        String query = "PREFIX poi: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-2#>\n" +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX pub: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-14#>\n" +
                "PREFIX station: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-8#>\n" +
                "PREFIX po: <http://purl.org/ontology/po/>\n" +
                "\n" +
                "SELECT DISTINCT ?name ?address ?popularityRating ?covidRating ?popularItemName\n" +
                "WHERE {\n" +
                "  SERVICE <http://ec2-52-86-45-247.compute-1.amazonaws.com:3030/PubsDataset> {\n" +
                "\n" +
                "  <"+ URL +"> pub:hasName ?name .\n" +
                "  <"+ URL +"> pub:hasAddress ?address .\n" +
                "  <"+ URL +"> pub:hasPopularityRating ?popularityRating .\n" +
                "  <"+ URL +"> pub:hasCovidSafetyRating ?covidRating .\n" +
                "  <"+ URL +"> pub:hasPopularItem ?popularItem.\n" +
                "  ?popularItem pub:hasPopularItemName ?popularItemName.\n" +
                "  }\n" +
                "}\n";
        return query;

    }

    public String getStationsDetailsQuery() {
        String query = "PREFIX poi: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-2#>\n" +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX pub: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-14#>\n" +
                "PREFIX station: <http://www.semanticweb.org/sudhanvahebbale/ontologies/2020/10/untitled-ontology-8#>\n" +
                "PREFIX po: <http://purl.org/ontology/po/>\n" +
                "PREFIX st: <http://ns.inria.fr/sparql-template/>\n" +
                "\n" +
                "SELECT DISTINCT ?name ?covidRating ?popularityRating ?platformCount ?zoneColor\n" +
                "WHERE {\n" +
                "  SERVICE <http://ec2-54-224-109-188.compute-1.amazonaws.com:3030/StationsDataset> {\n" +
                "\n" +
                "  <"+ URL +"> station:hasName ?name .\n" +
                "  <"+ URL +"> station:hasPopularityRating ?popularityRating .\n" +
                "  <"+ URL +"> station:hasCovidSafetyRating ?covidRating .\n" +
                "    <"+ URL +"> station:hasPlatformCount ?platformCount.\n" +
                "    <"+ URL +"> station:isInZone ?zone.\n" +
                "    ?zone station:hasZoneColor ?zoneColor.\n" +
                "  }\n" +
                "}\n";
        return query;
    }
}