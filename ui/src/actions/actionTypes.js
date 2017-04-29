export const GET_RATES = 'GET_RATES';
export const DOWNLOAD_EXCEL = 'DOWNLOAD_EXCEL';
export const CHANGE_DATE_RANGE = 'CHANGE_DATE_RANGE';

export const startAction = type => `START_${type}`;
export const successAction = type => `SUCCESS_${type}`;
export const failAction = type => `FAIL_${type}`;