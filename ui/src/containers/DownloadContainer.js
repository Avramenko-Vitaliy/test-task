import React, { PureComponent } from 'react';
import ImmutablePropTypes from 'react-immutable-proptypes';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import moment from 'moment';

import Download from '../components/Download';

import { getRatesByDate, downloadExcel } from '../actions';

import { getDateRange } from '../selectors';
import { getRates } from '../selectors';

const mapStateToProps = state => ({
    startDate: getDateRange(state).get('startDate'),
    endDate: getDateRange(state).get('endDate'),
    rates: getRates(state)
});

@connect(mapStateToProps, { getRatesByDate, downloadExcel })
export default class DownloadContainer extends PureComponent {
    static propTypes = {
        startDate: PropTypes.number,
        endDate: PropTypes.number,
        rates: ImmutablePropTypes.list,
        getRatesByDate: PropTypes.func.isRequired,
        downloadExcel: PropTypes.func.isRequired
    };

    handleClickDownloadExchangeRate = () => {
        if (this.props.startDate && this.props.endDate) {
            const startDate = moment(this.props.startDate).format('YYYY-MM-DD');
            const endDate = moment(this.props.endDate).format('YYYY-MM-DD');
            this.props.getRatesByDate(startDate, endDate);
        }
    };

    handleClickDownloadExcel = () => {
        this.props.downloadExcel(this.props.rates);
    };

    render() {
        return (
            <Download
                onClickDownloadExchangeRate={this.handleClickDownloadExchangeRate}
                onClickDownloadExcel={this.handleClickDownloadExcel}
            />
        );
    }
}
