import { fromJS } from 'immutable';

import { GET_RATES, successAction } from '../actions/actionTypes';

export default function (state = fromJS([]), action) {
    switch (action.type) {
        case successAction(GET_RATES):
            return fromJS(action.response.data);
        default:
            return state;
    }
}