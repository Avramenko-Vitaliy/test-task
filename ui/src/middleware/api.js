import axios from 'axios';

import { startAction, successAction, failAction } from '../actions/actionTypes';

const API_PREFIX = 'http://localhost:8080/';

const instance = axios.create({
    baseURL: API_PREFIX,
    timeout: 1000,
    withCredentials: true,
});

export const callApi = (method = 'get', endpoint, body) =>
    instance({ url: endpoint, method, data: body });

export const CALL_API = Symbol('CALL_API');

export default store => next => (action) => {
    const callAPI = action[CALL_API];

    if (typeof callAPI === 'undefined') {
        return next(action);
    }

    let { endpoint } = callAPI;
    const { type, method = 'get', body = {} } = callAPI;

    if (typeof endpoint === 'function') {
        endpoint = endpoint(store.getState());
    }

    if (typeof endpoint !== 'string') {
        throw new Error('Specify a string endpoint URL.');
    }

    if (typeof type !== 'string') {
        throw new Error('Expected action type to be string.');
    }
    console.log('endpoint, type, method', endpoint, type, method);

    const actionWith = (data) => {
        const finalAction = {
            ...action,
            ...data,
        };

        delete finalAction[CALL_API];

        return finalAction;
    };

    store.dispatch(
        actionWith({
            type: startAction(type),
        }),
    );
    return callApi(method, endpoint, body).then(
        (response) => {
            console.log('api success', response);
            return store.dispatch(
                actionWith({
                    response,
                    type: successAction(type),
                }),
            );
        },
        (error) => {
            if (!error.response) {
                return store.dispatch(
                    actionWith({
                        type: failAction(type),
                        error: error.message || 'Error happened during API call',
                    }),
                );
            }
            return store.dispatch(
                actionWith({
                    type: failAction(type),
                    error: error.response.data || 'Error happened during API call',
                }),
            );
        },
    );
};
