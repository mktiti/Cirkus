<template>
    <div id="card">
        <span id="name-label">
            <router-link :to="'/games/' + game.name" >{{ game.name }}</router-link>
        </span>
        <div id="details">
            <div id="owner-label">
                by <router-link :to="'/users/' + game.owner" >{{ game.owner }}</router-link>
            </div>
            <div v-if="game.match">PvP game</div>
            <div v-else>Challenge</div>
            <div>
                <a :href="downloadPath" :download="game.name + '-api.jar'">Download api</a>
            </div>
        </div>
    </div>
</template>

<script>
    import {axiosConfig} from "../main";

    export default {
        name: "GameCard",
        props: [
            'game'
        ],
        data() {
            return {
                downloadPath: ''
            };
        },
        watch: {
            game: function () {
                this.updateDownloadPath();
            }
        },
        mounted: function () {
            this.updateDownloadPath();
        },
        methods: {
            updateDownloadPath: function () {
                this.downloadPath = axiosConfig.baseURL + 'games/' + this.game.name + '/api'
            }
        }
    }
</script>

<style scoped>
    #card {
        padding: 30px;
        border: 2px black solid;
        margin: 20px;
        text-align: left;
    }

    #details {
        display: inline-block;
        padding-left: 20px;
        vertical-align: middle;
    }

    #name-label {
        font-size: x-large;
        vertical-align: middle;
    }

    #card a {
        text-decoration: none;
    }
</style>