import {createStore, applyMiddleware, compose} from 'redux';
import thunk from 'redux-thunk';

import api from '../middleware/api';

import rootReducer from '../reducers';

const devtools = (process.env.NODE_ENV === 'development' && window.devToolsExtension) || (() => noop => noop);

const middlewares = [
    thunk,
    api,
];

const enhancers = [
    applyMiddleware(...middlewares),
    devtools()
];
export default createStore(rootReducer, compose(...enhancers));
