function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

var goodsApi = Vue.resource("/goods{/id}")
var salesApi = Vue.resource("/sales{/id}")

Vue.component('good-form', {
    props: ['goods', 'goodAttr'],
    data: function () {
        return {
            name: '',
            priority: '',
            id: ''
        }
    },
    watch: {
        goodAttr: function(newVal, oldVal) {
            this.name = newVal.name;
            this.priority = newVal.priority;
            this.id = newVal.id;
        }
    },
    template:
        '<div>' +
        '<input type="text" placeholder="Enter name" v-model="name"/>' +
        '<input type="text" placeholder="Enter priority" v-model="priority"/>' +
        '<input type="button" value="Save" @click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            var good = { name: this.name, priority: this.priority };
            if (this.id) {
                goodsApi.update({ id: this.id }, good).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.goods, data.id);
                        this.goods.splice(index, 1, data);
                        this.name = '';
                        this.priority = '';
                        this.id = '';
                    })
                )
            } else {
                goodsApi.save({}, good).then(result =>
                    result.json().then(data => {
                        this.goods.push(data);
                        this.name = '';
                        this.priority = '';
                    })
                )
            }
        }
    }
});

Vue.component('good-row', {
    props: ['good', 'editMethod', 'goods'],
    data: function() {
        goodCount: ''
    },
    template:
        '<div>' +
        '<i>{{ good.id }})</i> {{ good.name }} {{ good.priority }}' +
        '<span style="position: absolute; right: 0">' +
        '<input type="text" placeholder="Count" v-model="goodCount"/>' +
        '<input type="button" value="Add" @click="del"/>' +
        '</span>' +
        '</div>',
    methods: {
        edit: function () {
            this.editMethod(this.good);
        },
        del: function () {
            goodsApi.remove({ id: this.good.id }).then(result => {
                if (result.ok) {
                    this.goods.splice(this.goods.indexOf(this.good), 1)
                }
            })
        }
    }
});

Vue.component('goods-list', {
    props: ['goods'],
    data: function() {
        return {
            good: null
        }
    },
    template:
        '<div style="position: relative; width: 500px">' +
        '<good-form :goods="goods" :goodAttr="good"/>' +
        '<good-row v-for="good in goods" :good="good" :editMethod="editMethod"/>' +
        '</div>',
    created: function () {
        goodsApi.get().then(result =>
            result.json().then(data =>
                data.forEach(good => this.goods.push(good))
            )
        )
    },
    methods: {
        editMethod: function (good) {
            this.good = good;
        }
    }
});

var app = new Vue({
    el: '#customer',
    template:
        '<div>' +
        '<h3>All goods:</h3>' +
        '<goods-list :goods="goods"/>' +
        '</div>',
    data: {
        goods: []
    }
});