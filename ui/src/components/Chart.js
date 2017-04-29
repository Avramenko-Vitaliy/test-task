import React, { Component } from 'react';
import PropTypes from 'prop-types';
import Highcharts from 'highcharts/highstock';

import { chartConfig } from '../components/chartConfig';

export default class Chart extends Component {
    static propTypes = {
        yExtremes: PropTypes.objectOf(PropTypes.number)
    };

    componentDidMount() {
        this.chart = Highcharts.stockChart('chartContainer', { ...chartConfig });
    }

    componentWillUpdate(nextProps) {
        this.chart.series[0].setData(nextProps.series);
        this.chart.yAxis[0].setExtremes(
            nextProps.yExtremes.min,
            nextProps.yExtremes.max,
            false,
        );
        this.chart.redraw();
    }

    render() {
        return <div id="chartContainer" />;
    }
}