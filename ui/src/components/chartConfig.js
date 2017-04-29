import Highcharts from 'highcharts/highstock';

export const chartConfig = {
    title: {
        text: false,
    },
    scrollbar: {
        enabled: false,
    },
    exporting: {
        enabled: false,
    },
    navigator: {
        enabled: false,
    },
    xAxis: {
        type: 'datetime',
        gridLineDashStyle: 'dash',
        gridLineWidth: 1,
        ordinal: false,
        startOnTick: false,
        endOnTick: false,
    },
    yAxis: {
        gridLineDashStyle: 'dash',
        opposite: false,
        startOnTick: false,
        endOnTick: false,
        title: {
            text: 'Exchange rate'
        }
    },
    legend: {
        enabled: false
    },
    plotOptions: { // fix www.highcharts.com/errors/12
        area: {
            turboThreshold: 0,
        },
    },
    rangeSelector: false,
    series: [{
        type: 'area',
        name: 'USD',
        fillColor: {
            linearGradient: {
                x1: 0,
                y1: 0,
                x2: 0,
                y2: 1,
            },
            stops: [
                [0,  Highcharts.getOptions().colors[0]],
                [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')],
            ],
        }
    }]
};