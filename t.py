tests = int(input())
for i in range(0, tests):
    digits = list(map(int, input().split()))
    able = True
    ans = 0
    while able:
        cur = ans + 1
        needed = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        while cur > 0:
            needed[cur % 10] += 1
            cur //= 10

        for j in range(0, 9):
            if digits[j] < needed[j]:
                able = False
                break

            digits[j] -= needed[j]

        if able:
            ans += 1
    print(ans)
