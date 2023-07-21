import CalendarGenerator from '../../src/Logic/CalendarGenerator';

const testMonth = (dateStr, expected) => {
  const result = CalendarGenerator.generate(new Date(dateStr));
  
  console.log(`Generated Calendar for ${dateStr}:`);
  console.log(JSON.stringify(result, null, 2)); // this will pretty-print the array
  expect(result).toEqual(expected);
};

describe('CalendarGenerator', () => {

  test('Calendar for May 2023', () => {
    const expected = [
      [30, 1, 2, 3, 4, 5, 6],
      [7, 8, 9, 10, 11, 12, 13],
      [14, 15, 16, 17, 18, 19, 20],
      [21, 22, 23, 24, 25, 26, 27],
      [28, 29, 30, 31, 1, 2, 3],
      [4, 5, 6, 7, 8, 9, 10]
    ];
    testMonth('2023-05-01', expected);
  });

});
