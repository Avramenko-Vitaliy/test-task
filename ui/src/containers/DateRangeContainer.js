import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import moment from 'moment';

import DateRange from '../components/DateRange';

import { changeDateRange } from '../actions';

import { getDateRange } from '../selectors';

const mapStateToProps = state => ({
    startDate: getDateRange(state).get('startDate'),
    endDate: getDateRange(state).get('endDate')
});

@connect(mapStateToProps, { changeDateRange})
export default class DateRangeContainer extends PureComponent {
    static propTypes = {
        startDate: PropTypes.number,
        endDate: PropTypes.number
    };

    handleChangeStartDate = (date) => {
        this.props.changeDateRange(date.valueOf(), this.props.endDate);
    };

    handleChangeEndDate = (date) => {
        this.props.changeDateRange(this.props.startDate, date.valueOf());
    };

    validateStartDate = current => {
        if (this.props.endDate) {
            return current.isBefore(this.props.endDate);
        }
        return true;
    };

    validateEndDate = current => {
        if (this.props.startDate) {
            return current.isBefore(moment()) && current.isAfter(this.props.startDate);
        }
        return true;
    };

    render() {
        return (
            <DateRange
                startDate={this.props.startDate}
                endDate={this.props.endDate}
                onValidateStartDate={this.validateStartDate}
                onValidateEndDate={this.validateEndDate}
                onChangeStartDate={this.handleChangeStartDate}
                onChangeEndDate={this.handleChangeEndDate}
            />
        );
    }
}
