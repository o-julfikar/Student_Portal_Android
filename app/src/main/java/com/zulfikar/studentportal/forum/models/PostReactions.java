package com.zulfikar.studentportal.forum.models;

import java.util.List;
import java.util.Map;

public class PostReactions {
    private int count;
    private Map<String, ReactionsData> data;

    private static String indent(int times) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < times; i++) indent.append("    ");
        return indent.toString();
    }

    public String toString() {
        StringBuilder dataString = new StringBuilder();
        String sep = "";
        for (Map.Entry<String, ReactionsData> entrySet : this.data.entrySet()) {
            dataString.append(sep).append(indent(2)).append(entrySet.getKey()).append(":\n");
            dataString.append(sep).append(indent(2)).append("{\n");
            dataString.append(entrySet.getValue());
            dataString.append(indent(2)).append("}");
            sep = ",\n";
        }
        return "{" + "\n" +
                indent(1) + "Count: " + count + "\n" +
                indent(1) + "Data: " + "\n" +
                indent(1) + "{" + "\n" +
                dataString.toString() + "\n" +
                indent(1) + "}" + "\n" +
                "}";

    }


    public static class ReactionsData {
        private ReactionInfo info;
        private List<ReactionData> data;

        public String toString() {
            StringBuilder dataString = new StringBuilder();
            String sep = "";
            for (ReactionData reactionData : this.data) {
                dataString.append(sep).append(reactionData);
                sep = ",\n";
            }
            return indent(3) + "info:" + "\n" +
                    info.toString() + ",\n" +
                    indent(3) + "data:" + "\n" +
                    indent(3) + "[\n" +
                    dataString.toString() + "\n" +
                    indent(3) + "]\n"
                    ;
        }

        public static class ReactionInfo {
            private String image;
            private int count;

            public String toString() {
                return indent(3) + "{" + "\n" +
                        indent(4) + "image: " + image + "\n" +
                        indent(4) + "count: " + count + "\n" +
                        indent(3) + "}";
            }
        }

        public static class ReactionData {
            private int reaction_id;
            private int user_bracu_id;
            private String user_name;
            private String user_photo;

            public String toString() {
                return indent(4) + "{" + "\n" +
                        indent(5) + "reaction_id: " + reaction_id + "\n" +
                        indent(5) + "user_bracu_id: " + user_bracu_id + "\n" +
                        indent(5) + "user_name: " + user_name + "\n" +
                        indent(5) + "user_photo: " + user_photo + "\n" +
                        indent(4) + "}";
            }
        }
    }

}
