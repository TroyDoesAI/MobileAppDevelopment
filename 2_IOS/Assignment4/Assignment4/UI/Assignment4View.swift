import SwiftUI

// Main View
struct Assignment4View: View {
    // Input string for postfix evaluation
    @State private var expression: String = ""
    // The result string of the postfix evaluation
    @State private var result: String = ""
    // The date to display in the calendar
    @State private var date: Date = Date()
    // Helper to generate calendar arrays
    private var calendarGenerator = CalendarGenerator()

    // Date formatter to display date
    private var monthYearFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "MMMM yyyy"
        return formatter
    }()

    var body: some View {
        VStack {
            // Postfix Calculator UI
            HStack {
                Image(systemName: "plus.slash.minus") // Postfix Calculator icon
                Text("Postfix Calculator")
            }
            .font(.title)
            .padding(.bottom, 20)
            TextField("Expression", text: $expression, onCommit: evaluateExpression)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding(.horizontal, 20)
            TextField("Result", text: $result)
                .disabled(true)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding(.horizontal, 20)
            HStack {
                Button(action: evaluateExpression) {
                    Text("Evaluate")
                        .padding()
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                }
                Button(action: clearFields) {
                    Text("Clear")
                        .padding()
                        .background(Color.red)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                }
            }
            .padding(.top, 20)
            // Calendar Generator UI
            HStack {
                Image(systemName: "calendar") // Calendar Generator icon
                Text("Calendar Generator")
            }
            .font(.title)
            .padding(.top, 20)
            Text(monthYearFormatter.string(from: date))
                .font(.title2)
                .padding(.bottom, 20)
            ForEach(generateCalendarArray(), id: \.self) { week in
                HStack {
                    ForEach(week, id: \.self) { day in
                        Text("\(day)")
                            .frame(width: 30, height: 30, alignment: .center)
                    }
                }
            }
            HStack {
                Button(action: previousMonth) {
                    Text("Previous")
                        .padding()
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                }
                Button(action: currentMonth) {
                    Text("Today")
                        .padding()
                        .background(Color.green)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                }
                Button(action: nextMonth) {
                    Text("Next")
                        .padding()
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                }
            }
            .padding(.top, 20)
            Spacer()
        }
        .padding()
    }
    
    // Function to evaluate postfix expression
    func evaluateExpression() {
        do {
            let calculator = PostfixCalculator()
            let evaluation = try calculator.parse(expression: expression)
            result = String(evaluation)
        } catch {
            result = "Error: \(error)"
        }
    }

    // Function to clear the input and result fields
    func clearFields() {
        expression = ""
        result = ""
    }

    // Function to generate a 2D array representing the current calendar month
    func generateCalendarArray() -> [[Int]] {
        let components = Calendar.current.dateComponents([.year, .month], from: date)
        return calendarGenerator.generate(yearAndMonth: components)
    }

    // Function to go to the previous month in the calendar
    func previousMonth() {
        date = Calendar.current.date(byAdding: .month, value: -1, to: date)!
    }

    // Function to go to the next month in the calendar
    func nextMonth() {
        date = Calendar.current.date(byAdding: .month, value: 1, to: date)!
    }

    // Function to go to the current month in the calendar
    func currentMonth() {
        date = Date()
    }
}

#if !TESTING
struct Assignment4View_Previews: PreviewProvider {
    static var previews: some View {
        Assignment4View()
    }
}
#endif
