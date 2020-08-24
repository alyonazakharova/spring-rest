function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

var salesApi = Vue.resource("/sales{/id}")

// Vue.component('sale-form', {
//     props: ['sales', 'sale'],
//     data: function () {
//         return {
//             good: '',
//             goodCount: '',
//             createDate: ''
//         }
//     },
//     watch: {
//         saleAttr: function(newVal, oldVal) {
//             this.good = newVal.good;
//             this.goodCount = newVal.goodCount;
//             this.createDate = newVal.createDate;
//         }
//     },
//     template:
//         '<div>' +
//         '<input type="text" placeholder="Enter good id" v-model="good"' +
//         '<input type="text" placeholder="Enter good count" v-model="goodCount"/>' +
//         '<input type="text" placeholder="Enter create date" v-model="createDate"/>' +
//         '<input type="button" value="Save" @click="save"/>' +
//         '</div>',
//     methods: {
//         save: function () {
//             var sale = { good: this.good, goodCount: this.goodCount, createDate: this.createDate };
//             if (this.id) {
//                 salesApi.update({ id: this.id }, sale).then(result =>
//                     result.json().then(data => {
//                         var index = getIndex(this.sales, data.id);
//                         this.sales.splice(index, 1, data);
//                         this.good = '';
//                         this.goodCount = '';
//                         this.createDate = '';
//                     })
//                 )
//             } else {
//                 salesApi.save({}, sale).then(result =>
//                     result.json().then(data => {
//                         this.sales.push(data);
//                         this.good = '';
//                         this.goodCount = '';
//                         this.createDate = '';
//                     })
//                 )
//             }
//         }
//     }
// });

Vue.component('sale-row', {
    props: ['sale', 'editMethod', 'sales'],
    template:
        '<div>' +
        '<i>{{ sale.id }})</i> {{ sale.good.name }} {{ sale.goodCount }} {{ sale.createDate }}' +
        '<span style="position: absolute; right: 0">' +
        '<input type="button" value="W1" @click="w1"/>' +
        '<input type="button" value="W2" @click="w2"/>' +
        '</span>' +
        '</div>',
    methods: {
        edit: function () {
            this.editMethod(this.sale);
        },
        del: function () {
            salesApi.remove({ id: this.sale.id }).then(result => {
                if (result.ok) {
                    this.sales.splice(this.sales.indexOf(this.sale), 1)
                }
            })
        }
    }
});

Vue.component('sales-list', {
    props: ['sales'],
    data: function() {
        return {
            sale: null
        }
    },
    template:
        '<div style="position: relative; width: 500px">' +
        '<sale-form :sales="sales" :saleAttr="saleAttr"/>' +
        '<sale-row v-for="sale in sales" :sale="sale" :editMethod="editMethod"/>' +
        '</div>',
    created: function () {
        salesApi.get().then(result =>
            result.json().then(data =>
                data.forEach(sale => this.sales.push(sale))
            )
        )
    },
    methods: {
        editMethod: function (sale) {
            this.sale = sale;
        }
    }
});

var sales = new Vue({
    el: '#sales',
    template:
        '<div>' +
        '<h3>All sales:</h3>' +
        '<sales-list :sales="sales"/>' +
        '</div>',
    data: {
        sales: []
    }
});