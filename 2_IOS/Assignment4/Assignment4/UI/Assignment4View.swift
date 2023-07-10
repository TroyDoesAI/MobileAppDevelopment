import SwiftUI

// This is a SwiftUI View that contains both a postfix calculator and a calendar generator.
struct Assignment4View: View {
    // Two state properties for the expression to be evaluated and the result of the evaluation.
    @State private var expression: String = ""
    @State private var result: String = ""

    // The currently selected date and the calendar generator instance.
    @State private var date: Date = Date()
    private var calendarGenerator = CalendarGenerator()
    
    // DateFormatter to format the date to be shown in the calendar section.
    private var dateFormatter: DateFormatter = {
        let df = DateFormatter()
        df.dateFormat = "MMMM yyyy"
        return df
    }()
    
    // The body of the SwiftUI view.
    var body: some View {
        // The content is stacked vertically in a VStack.
        VStack {
            // Title for the Postfix Calculator section.
            Text("Postfix Calculator")
                .font(.title)
                .padding(.bottom, 20)
            
            // TextField for the user to enter the expression to be evaluated.
            TextField("Expression", text: $expression, onCommit: evaluateExpression)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding(.horizontal, 20)
            
            // TextField to display the result of the evaluation.
            TextField("Result", text: $result)
                .disabled(true)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding(.horizontal, 20)
            
            // Evaluate and Clear buttons.
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
            
            // Title for the Calendar Generator section.
            Text("Calendar Generator")
                .font(.title)
                .padding(.top, 20)
            
            // Displays the currently selected month and year.
            Text(dateFormatter.string(from: date))
                .font(.title2)
                .padding(.bottom, 20)
            
            // Displays the calendar for the currently selected month.
            ForEach(generateCalendarArray(), id: \.self) { week in
                HStack {
                    ForEach(week, id: \.self) { day in
                        Text(day > 0 ? "\(day)" : "")
                            .frame(width: 30, height: 30, alignment: .center)
                    }
                }
            }
            
            // Previous, Today and Next buttons.
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
            
            // Spacer to push the content to the top of the screen.
            Spacer()
        }
        .padding()
    }
    
    // Function to evaluate the expression entered by the user.
    func evaluateExpression() {
        do {
            let calculator = PostfixCalculator()
            let evaluation = try calculator.parse(expression: expression)
            result = String(evaluation)
        } catch {
            result = "Error: \(error)"
        }
    }

    // Function to clear the expression and result fields.
    func clearFields() {
        expression = ""
        result = ""
    }
    
    // Function to generate the calendar for the currently selected month.
    func generateCalendarArray() -> [[Int]] {
        let components = Calendar.current.dateComponents([.year, .month], from: date)
        return calendarGenerator.generate(yearAndMonth: components)
    }
    
    // Functions to change the currently selected month.
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

// This is a preview of the SwiftUI view for use in the Xcode canvas.
#if !TESTING
struct Assignment4View_Previews: PreviewProvider {
    static var previews: some View {
        Assignment4View()
    }
}
#endif

