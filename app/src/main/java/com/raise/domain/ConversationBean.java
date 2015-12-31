package com.raise.domain;

import java.util.List;

/**
 * Created by VITON-CEO on 2015/9/24.
 */
public class ConversationBean {


    /**
     * answer : {"text":"今天是2015年09月24日 乙未年八月十二 星期四","type":"T"}
     * moreResults : [{"answer":{"text":"歌曲名∶今天星期几 (钟欣桐独唱)歌手∶Twins（钟欣桐、蔡卓妍）作曲∶吕绍淳,作词∶何启弘,演唱∶钟欣桐,专辑∶八十块环游世界(台湾版),钟欣桐-今天星期几,星期一戒掉等你,电话的坏习惯,星期二故意把你,喜欢的发型弄乱,星期三保养受损的心,不再被眼泪感染,星期四忘掉你我,几乎成功了一半,星期五催眠自己,真的能够勇敢,星期六房间换了,钥匙和新的开关,星期天逛街选化妆品,用好的心情来买单,才发现甩","type":"T"},"operation":"ANSWER","rc":0,"service":"baike","text":"今天星期几？"}]
     * operation : ANSWER
     * rc : 0
     * service : datetime
     * text : 今天星期几？
     */

    private AnswerEntity answer;
    private String operation;
    private int rc;
    private String service;
    private String text;
    private List<MoreResultsEntity> moreResults;

    public void setAnswer(AnswerEntity answer) {
        this.answer = answer;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setMoreResults(List<MoreResultsEntity> moreResults) {
        this.moreResults = moreResults;
    }

    public AnswerEntity getAnswer() {
        return answer;
    }

    public String getOperation() {
        return operation;
    }

    public int getRc() {
        return rc;
    }

    public String getService() {
        return service;
    }

    public String getText() {
        return text;
    }

    public List<MoreResultsEntity> getMoreResults() {
        return moreResults;
    }

    public static class AnswerEntity {
        /**
         * text : 今天是2015年09月24日 乙未年八月十二 星期四
         * type : T
         */

        private String text;
        private String type;

        public void setText(String text) {
            this.text = text;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public String getType() {
            return type;
        }
    }

    public static class MoreResultsEntity {
        /**
         * answer : {"text":"歌曲名∶今天星期几 (钟欣桐独唱)歌手∶Twins（钟欣桐、蔡卓妍）作曲∶吕绍淳,作词∶何启弘,演唱∶钟欣桐,专辑∶八十块环游世界(台湾版),钟欣桐-今天星期几,星期一戒掉等你,电话的坏习惯,星期二故意把你,喜欢的发型弄乱,星期三保养受损的心,不再被眼泪感染,星期四忘掉你我,几乎成功了一半,星期五催眠自己,真的能够勇敢,星期六房间换了,钥匙和新的开关,星期天逛街选化妆品,用好的心情来买单,才发现甩","type":"T"}
         * operation : ANSWER
         * rc : 0
         * service : baike
         * text : 今天星期几？
         */

        private AnswerEntity answer;
        private String operation;
        private int rc;
        private String service;
        private String text;

        public void setAnswer(AnswerEntity answer) {
            this.answer = answer;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public void setRc(int rc) {
            this.rc = rc;
        }

        public void setService(String service) {
            this.service = service;
        }

        public void setText(String text) {
            this.text = text;
        }

        public AnswerEntity getAnswer() {
            return answer;
        }

        public String getOperation() {
            return operation;
        }

        public int getRc() {
            return rc;
        }

        public String getService() {
            return service;
        }

        public String getText() {
            return text;
        }

        public static class AnswerEntity {
            /**
             * text : 歌曲名∶今天星期几 (钟欣桐独唱)歌手∶Twins（钟欣桐、蔡卓妍）作曲∶吕绍淳,作词∶何启弘,演唱∶钟欣桐,专辑∶八十块环游世界(台湾版),钟欣桐-今天星期几,星期一戒掉等你,电话的坏习惯,星期二故意把你,喜欢的发型弄乱,星期三保养受损的心,不再被眼泪感染,星期四忘掉你我,几乎成功了一半,星期五催眠自己,真的能够勇敢,星期六房间换了,钥匙和新的开关,星期天逛街选化妆品,用好的心情来买单,才发现甩
             * type : T
             */

            private String text;
            private String type;

            public void setText(String text) {
                this.text = text;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getText() {
                return text;
            }

            public String getType() {
                return type;
            }
        }
    }
}
