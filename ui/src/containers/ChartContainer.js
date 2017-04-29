import React, { PureComponent } from 'react';
import ImmutablePropTypes from 'react-immutable-proptypes';
import moment from 'moment';
import { connect } from 'react-redux';

import { getRates } from '../selectors';

import Chart from  '../components/Chart';

const mapStateToProps = state => ({
    rates: getRates(state)
});

@connect(mapStateToProps, {})
export default class ChartContainer extends PureComponent {
    static propTypes = {
        rates: ImmutablePropTypes.list
    };

    render() {
        const series = this.props.rates
            .reduce((result, current) => {
                result.push([moment(current.get('dateRate'), 'YYYY-MM-DD').valueOf(), current.get('rate')]);
                return result;
            }, []);
        const max = this.props.rates.max((e1, e2) => e1.get('rate') - e2.get('rate'));
        const min = this.props.rates.min((e1, e2) => e1.get('rate') - e2.get('rate'));
        const yExtremes = { max: max && max.get('rate'), min: min && min.get('rate') };

        return <Chart series={series} yExtremes={yExtremes} />
    }
}
