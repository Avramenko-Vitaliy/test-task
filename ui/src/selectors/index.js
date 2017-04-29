export const getRates = state => {
    const rates = state.get('rates');
    rates.map((item, index, array) => {
        if (!item.get('rate')) {
            const rate = array.getIn([index - 1, 'rate']);
            return item.merge({ rate });
        }
        return item;
    });
    return rates
};

export const getDateRange = state => state.get('dateRange');
