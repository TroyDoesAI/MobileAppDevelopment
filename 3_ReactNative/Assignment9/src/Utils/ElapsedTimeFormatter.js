// Utils/ElapsedTimeFormatter.js
export const formatElapsedTime = mostRecentDate => {
  if (!mostRecentDate || isNaN(mostRecentDate.getTime())) {
    return ''; // Return an empty string if the date is not valid (e.g., when there are no messages)
  }

  const now = new Date();
  const diffInSeconds = Math.floor((now - mostRecentDate) / 1000);

  const timeIntervals = [
    {unit: 'secs', value: 60},
    {unit: 'mins', value: 60},
    {unit: 'hours', value: 24},
    {unit: 'days', value: Number.POSITIVE_INFINITY},
  ];

  let value = diffInSeconds;
  for (let interval of timeIntervals) {
    if (value < interval.value) {
      return `${value} ${interval.unit}`;
    }
    value = Math.floor(value / interval.value);
  }
};
