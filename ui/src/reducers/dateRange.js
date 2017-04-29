import { fromJS } from 'immutable';
import { CHANGE_DATE_RANGE } from '../actions/actionTypes';

const defaultState = fromJS({
    startDate: null,
    endDate: null
});

export default function dateRange(state = defaultState, action) {
    switch (action.type) {
        case CHANGE_DATE_RANGE:
            return state.merge({ startDate: action.startDate, endDate: action.endDate });
        default:
            return state;
    }
}