import SwiftUI

struct Assignment4View: View {
    @State private var expression: String = ""
    @State private var result: String = ""
    
    @State private var date: Date = Date()
    private var calendarGenerator = CalendarGenerator()
    private var dateFormatter: DateFormatter = {
        let df = DateFormatter()
        df.dateFormat = "MMMM yyyy"
        return df
    }()
    
    var body: some View {
        VStack {
            Text("Postfix Calculator")
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
            
            Text("Calendar Generator")
                .font(.title)
                .padding(.top, 20)
            
            Text(dateFormatter.string(from: date))
                .font(.title2)
                .padding(.bottom, 20)

            ForEach(generateCalendarArray(), id: \.self) { week in
                HStack {
                    ForEach(week, id: \.self) { day in
                        Text(day > 0 ? "\(day)" : "")
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

    func evaluateExpression() {
        do {
            let calculator = PostfixCalculator()
            let evaluation = try calculator.parse(expression: expression)
            result = String(evaluation)
        } catch {
            result = "Error: \(error)"
        }
    }

    func clearFields() {
        expression = ""
        result = ""
    }
    
    func generateCalendarArray() -> [[Int]] {
        let components = Calendar.current.dateComponents([.year, .month], from: date)
        return calendarGenerator.generate(yearAndMonth: components)
    }
    
    func previousMonth() {
        date = Calendar.current.date(byAdding: .month, value: -1, to: date) ?? date
    }
    
    func nextMonth() {
        date = Calendar.current.date(byAdding: .month, value: 1, to: date) ?? date
    }
    
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

