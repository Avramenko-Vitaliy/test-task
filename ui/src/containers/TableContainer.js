import React, { PureComponent } from 'react';
import ImmutablePropTypes from 'react-immutable-proptypes';
import { connect } from 'react-redux';

import Table from '../components/Table';

import { getRates } from '../selectors';

import { calculateRowSpan } from '../utils';

const mapStateToProps = state => ({
    rates: getRates(state)
});

@connect(mapStateToProps, {})
export default class TableContainer extends PureComponent {
    static propTypes = {
        rates: ImmutablePropTypes.list
    };

    renderRateData(rate) {
        if (rate) {
            return (
                <td rowSpan={rate.rowSpan ? rate.rowSpan + 1 : null}>
                    {rate.rate}
                </td>
            );
        }
        return null;
    }

    render() {
        const rates = calculateRowSpan(this.props.rates);
        return (
            <Table>
                {
                    this.props.rates.toJS().map(item =>
                        <tr key={item.dateRate}>
                            <td>{item.dateRate}</td>
                            {
                                this.renderRateData(rates.find(rate => rate.dateRate === item.dateRate))
                            }
                        </tr>
                    )
                }
            </Table>
        );
    }
}
