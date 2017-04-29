import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import { getRates } from '../actions';

import ChartContainer from './ChartContainer';
import TableContainer from './TableContainer';
import DateRangeContainer from './DateRangeContainer';
import DownloadContainer from './DownloadContainer';

@connect(null, { getRates })
export default class App extends PureComponent {
    static propTypes = {
        getRates: PropTypes.func.isRequired
    };

    componentDidMount() {
        this.props.getRates();
    }

    render() {
        return (
            <div className="form-horizontal">
                <br/>
                <DateRangeContainer/>
                <DownloadContainer/>
                <ChartContainer/>
                <TableContainer/>
            </div>
        );
    }
}