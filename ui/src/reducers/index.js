import { combineReducers } from 'redux-immutable';

import rates from './rates';
import dateRange from './dateRange';

export default combineReducers({ rates, dateRange });
