export const calculateRowSpan = (rates) => rates
    .reduce((result, current) => {
        const rate = result[result.length - 1];
        if (rate && (rate.rate === current.get('rate') || !current.get('rate'))) {
            rate.rowSpan++;
        } else {
            result.push({ dateRate: current.get('dateRate'), rate: current.get('rate'), rowSpan: 0 });
        }
        return result;
    }, []);
