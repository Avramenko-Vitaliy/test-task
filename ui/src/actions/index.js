import moment from 'moment';
import fileDownload from 'react-file-download';

import * as ActionTypes from './actionTypes';
import { CALL_API } from '../middleware/api';

const formatter = response => {
    if (!response.data || !response.data.length) {
        return response;
    }
    const first = response.data[0].dateRate;
    const last = response.data[response.data.length - 1].dateRate;
    const data = [];
    for (let i = moment(first); !moment(i).isSame(last, 'day'); i = moment(i).subtract(1, 'day')) {
        const rate = response.data.find(item => moment(i).isSame(item.dateRate, 'day'));
        data.push({ ...rate, dateRate: i.format('YYYY-MM-DD') });
    }
    response.data = data.map((item, index, array) => {
        if (!item.rate && index !== 0) {
            item.rate = array[index - 1].rate;
        }
        return item;
    });
    return response;
};

export const getRatesByDate = (start = null, end = null) => dispatch => (
    dispatch({
        [CALL_API]: {
            type: ActionTypes.GET_RATES,
            endpoint: `/rates?startDate=${start}&endDate=${end}`,
            formatter,
        }
    })
);

export const getRates = () => dispatch => (
    dispatch({
        [CALL_API]: {
            type: ActionTypes.GET_RATES,
            endpoint: `/rates`,
            formatter,
        }
    })
);

export const changeDateRange = (startDate, endDate) => ({
    type: ActionTypes.CHANGE_DATE_RANGE,
    startDate,
    endDate,
});

export const downloadExcel = (rates = []) => dispatch => (
    dispatch({
        [CALL_API]: {
            type: ActionTypes.DOWNLOAD_EXCEL,
            responseType: 'blob',
            endpoint: '/rates/excel',
            method: 'post',
            body: rates,
            formatter: (response) => fileDownload(response.data, 'exchange_rates.xls')
        }
    })
);
