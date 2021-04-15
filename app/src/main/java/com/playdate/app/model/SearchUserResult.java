

package com.playdate.app.model;

import java.util.List;

public class SearchUserResult {

    private Long totalCount;

    private Boolean incompleteResults;

    private List<Item> items;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "SearchUserResult{" +
                "totalCount=" + totalCount +
                ", incompleteResults=" + incompleteResults +
                ", items=" + items +
                '}';
    }

    public static class Item {

        private String login;

        private Long id;

        private String nodeId;

        private String avatarUrl;

        private String gravatarId;

        private String url;

        private String htmlUrl;

        private String followersUrl;

        private String followingUrl;

        private String gistsUrl;

        private String starredUrl;

        private String subscriptionsUrl;

        private String organizationsUrl;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getGravatarId() {
            return gravatarId;
        }

        public void setGravatarId(String gravatarId) {
            this.gravatarId = gravatarId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHtmlUrl() {
            return htmlUrl;
        }

        public void setHtmlUrl(String htmlUrl) {
            this.htmlUrl = htmlUrl;
        }

        public String getFollowersUrl() {
            return followersUrl;
        }

        public void setFollowersUrl(String followersUrl) {
            this.followersUrl = followersUrl;
        }

        public String getFollowingUrl() {
            return followingUrl;
        }

        public void setFollowingUrl(String followingUrl) {
            this.followingUrl = followingUrl;
        }

        public String getGistsUrl() {
            return gistsUrl;
        }

        public void setGistsUrl(String gistsUrl) {
            this.gistsUrl = gistsUrl;
        }

        public String getStarredUrl() {
            return starredUrl;
        }

        public void setStarredUrl(String starredUrl) {
            this.starredUrl = starredUrl;
        }

        public String getSubscriptionsUrl() {
            return subscriptionsUrl;
        }

        public void setSubscriptionsUrl(String subscriptionsUrl) {
            this.subscriptionsUrl = subscriptionsUrl;
        }

        public String getOrganizationsUrl() {
            return organizationsUrl;
        }

        public void setOrganizationsUrl(String organizationsUrl) {
            this.organizationsUrl = organizationsUrl;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "login='" + login + '\'' +
                    ", id=" + id +
                    ", nodeId='" + nodeId + '\'' +
                    ", avatarUrl='" + avatarUrl + '\'' +
                    ", gravatarId='" + gravatarId + '\'' +
                    ", url='" + url + '\'' +
                    ", htmlUrl='" + htmlUrl + '\'' +
                    ", followersUrl='" + followersUrl + '\'' +
                    ", followingUrl='" + followingUrl + '\'' +
                    ", gistsUrl='" + gistsUrl + '\'' +
                    ", starredUrl='" + starredUrl + '\'' +
                    ", subscriptionsUrl='" + subscriptionsUrl + '\'' +
                    ", organizationsUrl='" + organizationsUrl + '\'' +
                    '}';
        }
    }
}
